package hmap.core.hms.contact.service.impl;

import com.github.pagehelper.StringUtil;
import com.hand.hap.cache.impl.CacheManagerImpl;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.dto.SimpleStaffResponseDTO;
import hmap.core.hms.dto.StandardStaffResponseDTO;
import hmap.core.hms.contact.mapper.HmsStaffMapper;
import hmap.core.hms.contact.service.IHmsStaffService;
import hmap.core.search.StaffSearchRepository;
//import hmap.core.util.Chinese;
import hmap.core.util.PinyinUtils;
import hmap.core.util.StaffTransformer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.apache.lucene.document.LongPoint;
@Service
@Transactional
public class HmsStaffServiceImpl extends BaseServiceImpl<HmsStaff> implements IHmsStaffService {
  @Autowired
  private StaffSearchRepository staffSearchRepository;
  @Autowired
  IMessagePublisher messagePublisher;
  @Resource
  private HmsStaffMapper sm;
  @Autowired
  private CacheManagerImpl cml;
  @Autowired
  private ElasticsearchTemplate elasticsearchTemplate;
  @Autowired
  private Client esClient;
  private String path;
  private IndexReader reader;
  private IndexSearcher searcher;
  private Logger logger = LoggerFactory.getLogger(HmsStaffServiceImpl.class);

  @Override
  public StandardStaffResponseDTO selectStaffDtoByAccountNumber(IRequest iRequest, String arg) {

    HmsStaff staff = (HmsStaff) cml.getCache("staff").getValue(arg);
    if (staff == null) {
      staff = sm.selectByAccountNumber(arg);
      if (staff != null) {
        cml.getCache("staff").setValue(staff.getAccountNumber(), staff);
      }
    }
    if (staff != null) {
      StandardStaffResponseDTO staffResponseDto = StaffTransformer.changeDtoStandard(staff);
      return staffResponseDto;
    }
    return null;

  }

  @Override
  public HmsStaff selectByAccountNumber(String arg) {
    HmsStaff staff = (HmsStaff) cml.getCache("staff").getValue(arg);
    if (staff == null) {
      staff = sm.selectByAccountNumber(arg);
      if (staff != null) {
        cml.getCache("staff").setValue(staff.getAccountNumber(), staff);
      }
    }
    if (staff != null) {
      return staff;
    }
    return null;

  }

  @Override
  public void generateData(IRequest iRequest, HmsStaff[] staff) {
    for (HmsStaff op : staff) {
      if (staffSearchRepository.exists(op.getAccountNumber())) {
        continue;
      }
      try {
        String name = op.getUserName();
        op.setNamePinyin(PinyinUtils.getPinyin(name));
        op.setNamePinyinCapital(PinyinUtils.getPinyinCapital(name));
        op.setUserId(UUID.randomUUID().toString());
        // op.setAttribute10("PENDING");
        this.insertSelective(iRequest, op);

        cml.getCache("staff").setValue(op.getAccountNumber(), op);
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error=" + e.toString());
      } finally {
        messagePublisher.publish("public.es.staff.syn.insert", op);
      }
    }
  }

  @Override
  public void updateData(IRequest iRequest, HmsStaff[] staff) {
    // TODO Auto-generated method stub
    for (HmsStaff op : staff) {
      if (!staffSearchRepository.exists(op.getAccountNumber())) {
        continue;
      }
      try {
        String name = op.getUserName();
        op.setNamePinyin(PinyinUtils.getPinyin(name));
        op.setNamePinyinCapital(PinyinUtils.getPinyinCapital(name));
        op.setUserId(this.selectByAccountNumber(op.getAccountNumber()).getUserId());
        System.out.println(op.getUserId() + op.getHomeTown());

        System.out.println(this.selectByAccountNumber(op.getAccountNumber()));
        this.updateByPrimaryKeySelective(iRequest, op);
        cml.getCache("staff").remove(op.getAccountNumber());
        cml.getCache("staff").setValue(op.getAccountNumber(), op);
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error=" + e.toString());
      } finally {
        messagePublisher.publish("public.es.staff.syn.update", op);
      }
    }
  }

  @Override
  public void deleteData(IRequest iRequest, HmsStaff[] staff) {
    for (HmsStaff op : staff) {
      try {
        sm.deleteByAccountNumber(op.getAccountNumber());
        cml.getCache("staff").remove(op.getAccountNumber());
      } catch (Exception e) {
        e.printStackTrace();
        logger.error("error=" + e.toString());
      } finally {
        messagePublisher.publish("public.es.staff.syn.delete", op);
      }
    }
  }

  private void addStringField(Document doc, String name, String value) {
    String indexValue = value != null ? value : "";
    Field field = new StringField(name, indexValue, Field.Store.YES);
    doc.add(field);
    field = new SortedDocValuesField(name, new BytesRef(indexValue));
    doc.add(field);
  }

  private long accountNumberTransfer(String accountNumber) {
    long empNo = 0L;
    if (StringUtil.isEmpty(accountNumber)) {
      empNo = 0L;
      return empNo;
    }
    boolean result = accountNumber.trim().matches("[0-9]+");

    if (result == true) {
      empNo = Long.valueOf(accountNumber.trim());// 纯数字为正式员工，可直接转换
    } else {
      String regEx = "[^0-9]";
      Pattern p = Pattern.compile(regEx);
      Matcher m = p.matcher(accountNumber);
      String empStr = m.replaceAll("").trim();
      empNo = Long.valueOf(empStr) * 9999;// ，不是纯数字为外协员工需要处理，用于和正式员工进行区分
    }
    return empNo;
  }

  public void initLucene() throws IOException {
    IndexWriter indexWriter = this.getIndexWriter(path, true);
    List<HmsStaff> list = this.selectAll();
    for (HmsStaff sf : list) {
      Document doc = new Document();
      doc.add(new StringField("userName", sf.getUserName() != null ? sf.getUserName() : "",
          Field.Store.YES));

      doc.add(new StringField("accountNumber",
          sf.getAccountNumber() != null ? sf.getAccountNumber() : "", Field.Store.YES));
      long numericAccountNumber =
          this.accountNumberTransfer(sf.getAccountNumber() != null ? sf.getAccountNumber() : "");
      // doc.add(new LongPoint("accountNumberLong", numericAccountNumber));
      doc.add(new NumericDocValuesField("accountNumberLong", numericAccountNumber));
      doc.add(new StringField("empStatus", sf.getEmpStatus() != null ? sf.getEmpStatus() : "",
          Field.Store.YES));
      doc.add(new StringField("position", sf.getPosition() != null ? sf.getPosition() : "",
          Field.Store.YES));
      doc.add(
          new StringField("mobile", sf.getMobile() != null ? sf.getMobile() : "", Field.Store.YES));
      doc.add(new StringField("gender", sf.getGenderId() != null ? sf.getGenderId().toString() : "",
          Field.Store.YES));
      doc.add(
          new StringField("email", sf.getEmail() != null ? sf.getEmail() : "", Field.Store.YES));
      doc.add(
          new StringField("avatar", sf.getAvatar() != null ? sf.getAvatar() : "", Field.Store.YES));

      String namePinyin1 = sf.getNamePinyin() != null ? sf.getNamePinyin().toLowerCase() : "";
      this.addStringField(doc, "namePinyin", namePinyin1);

      doc.add(new StringField("namePinyinCapital",
          sf.getNamePinyinCapital() != null ? sf.getNamePinyinCapital().toLowerCase() : "",
          Field.Store.YES));

      doc.add(new StringField("homeTown", sf.getHomeTown() != null ? sf.getHomeTown() : "",
          Field.Store.YES));
      indexWriter.addDocument(doc);

    }
    indexWriter.close();

    this.init();// 将索引读入内存
  }

  public IndexWriter getIndexWriter(String indexPath, boolean create) throws IOException {
    Directory dir = FSDirectory.open(Paths.get(indexPath, new String[0]));
    Analyzer analyzer = new StandardAnalyzer();
    IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    if (create) {
      iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    } else {
      iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
    }
    IndexWriter writer = new IndexWriter(dir, iwc);
    return writer;
  }

  @PostConstruct
  public void init() {
    if (staffSearchRepository != null && staffSearchRepository.count() < 1) {
      List<HmsStaff> allStaff = this.selectAll(RequestHelper.newEmptyRequest());
      if (allStaff != null && allStaff.size() > 0) {
        staffSearchRepository.save(allStaff);
      }
    }
  }

  public boolean insertES() {
    List<HmsStaff> list = sm.selectAll();
    List<IndexQuery> queries = new ArrayList<IndexQuery>();
    for (HmsStaff staff : list) {
      IndexQuery indexQuery =
          new IndexQueryBuilder().withId(staff.getAccountNumber()).withObject(staff).build();
      queries.add(indexQuery);
    }
    elasticsearchTemplate.bulkIndex(queries);
    return true;
  }

  public List<SimpleStaffResponseDTO> search(String arg, int num, int eachPage)
      throws IOException, ParseException, InvalidTokenOffsetsException {

    List<HmsStaff> requestIdList = new ArrayList<HmsStaff>();
    Query userNameTerm = new WildcardQuery(new Term("userName", "*" + arg + "*"));
    Query userNameBoost = new BoostQuery(userNameTerm, 3f);
    Query accountNumberTermQuery = new PrefixQuery(new Term("accountNumber", arg));
    Query accountNumberTerm = new BoostQuery(accountNumberTermQuery, 5.0f);

    /**
     * 此处大量的冗余字段是为了处理的问题，当员工的姓氏存在多音字的情况下，需要检索可能存在的读音
     */
    // 多音字处理开始
    Query namePinyin = new WildcardQuery(new Term("namePinyin", "*" + arg + "*"));

    Query namePinyinBoost = new BoostQuery(namePinyin, 0.5f);
    Query namePinyin2 = new WildcardQuery(new Term("namePinyin", "*" + "|" + arg + "*"));

    Query namePinyinBoost2 = new BoostQuery(namePinyin2, 1f);
    // // 第一遍
    // Query namePinyinCapitalTermQuery = new TermQuery(new Term(
    // "namePinyinCapital", arg));
    // Query namePinyinCapitalTerm = new BoostQuery(
    // namePinyinCapitalTermQuery, 4.0f);
    //
    // // 第二遍
    // Query namePinyinCapitalPrefixQuery = new PrefixQuery(new Term(
    // "namePinyinCapital", arg));
    // Query namePinyinCapitalPrefix = new BoostQuery(
    // namePinyinCapitalPrefixQuery, 3.0f);
    Query namePinyinCapitalWildcardQuery1 =
        new WildcardQuery(new Term("namePinyinCapital", "*|" + arg + "|*"));
    Query namePinyinCapitalWildcard1 = new BoostQuery(namePinyinCapitalWildcardQuery1, 4.0f);
    // 第三遍
    Query namePinyinCapitalWildcardQuery =
        new WildcardQuery(new Term("namePinyinCapital", "*" + arg + "*"));
    Query namePinyinCapitalWildcard = new BoostQuery(namePinyinCapitalWildcardQuery, 2.0f);

    // Query namePinyinPrefixTerm = new PrefixQuery(
    // new Term("namePinyin", arg));

    // 多音字处理结束

    Query positionTerm = new WildcardQuery(new Term("position", "*" + arg + "*"));
    Query positionBoost = new BoostQuery(positionTerm, 0.5f);
    Query mobileTerm = new WildcardQuery(new Term("mobile", "*" + arg + "*"));
    Query emailTerm = new WildcardQuery(new Term("email", "*" + arg + "*"));
    Query homeTownTerm = new WildcardQuery(new Term("homeTown", "*" + arg + "*"));
    Query homeTownBoost = new BoostQuery(homeTownTerm, 0.5f);

    BooleanQuery booleanQuery = new BooleanQuery.Builder().add(userNameBoost, BooleanClause.Occur.SHOULD)
				.add(positionBoost, BooleanClause.Occur.SHOULD).add(mobileTerm, BooleanClause.Occur.SHOULD)
				.add(emailTerm, BooleanClause.Occur.SHOULD).add(homeTownBoost, BooleanClause.Occur.SHOULD)
				.add(namePinyinBoost, BooleanClause.Occur.SHOULD).add(namePinyinBoost2, BooleanClause.Occur.SHOULD)

				.add(namePinyinCapitalWildcard1, BooleanClause.Occur.SHOULD)

				.add(namePinyinCapitalWildcard, BooleanClause.Occur.SHOULD)

				.add(accountNumberTerm, BooleanClause.Occur.SHOULD).build();

    Sort sort = new Sort(new SortField("namePinyinCapital", Type.SCORE, false),
        new SortField("accountNumberLong", Type.LONG, false));
    TopDocs hits = null;

    hits = searcher.search(booleanQuery, num * eachPage, sort, true, true);
    ScoreDoc[] s = hits.scoreDocs;
    for (int i = ((num - 1) * eachPage); i < s.length; i++) {
      ScoreDoc sc = s[i];

      Document doc = searcher.doc(sc.doc);
      HmsStaff staff = new HmsStaff();

      if (doc.get("userName").indexOf(arg) != -1) {
        staff.setUserName(doc.get("userName").replaceAll(arg, arg));
      } else {
        staff.setUserName(doc.getField("userName").stringValue());
      }
      if (doc.get("accountNumber").indexOf(arg) != -1) {
        staff.setAccountNumber(doc.get("accountNumber").replaceAll(arg, arg));
      } else {
        staff.setAccountNumber(doc.getField("accountNumber").stringValue());
      }
      if (doc.get("email").indexOf(arg) != -1) {
        staff.setEmail(doc.get("email").replaceAll(arg, arg));
      } else {
        staff.setEmail(doc.getField("email").stringValue());
      }
      if (doc.get("mobile").indexOf(arg) != -1) {
        staff.setMobile(doc.get("mobile").replaceAll(arg, arg));
      } else {
        staff.setMobile(doc.getField("mobile").stringValue());
      }
      staff.setPosition(doc.getField("position").stringValue());

      staff.setHomeTown(doc.getField("homeTown").stringValue());

      staff.setEmpStatus(doc.getField("empStatus").stringValue());
      staff.setGenderId(Long.parseLong(doc.getField("gender").stringValue()));
      staff.setAvatar(doc.getField("avatar").stringValue());
      requestIdList.add(staff);
    }
    return StaffTransformer.changeListSimple(requestIdList);

  }

  public List<SimpleStaffResponseDTO> searchHighLight(String arg, int num, int eachPage)
      throws IOException, ParseException, InvalidTokenOffsetsException {

    List<HmsStaff> requestIdList = new ArrayList<HmsStaff>();
    Query userNameTerm = new WildcardQuery(new Term("userName", "*" + arg + "*"));
    Query userNameBoost = new BoostQuery(userNameTerm, 3f);
    Query accountNumberTermQuery = new PrefixQuery(new Term("accountNumber", arg));
    Query accountNumberTerm = new BoostQuery(accountNumberTermQuery, 5.0f);

    /**
     * 此处大量的冗余字段是为了处理的问题，当员工的姓氏存在多音字的情况下，需要检索可能存在的读音
     */
    // 多音字处理开始
    Query namePinyin = new WildcardQuery(new Term("namePinyin", "*" + arg + "*"));

    Query namePinyinBoost = new BoostQuery(namePinyin, 0.5f);
    Query namePinyin2 = new WildcardQuery(new Term("namePinyin", "*" + "|" + arg + "*"));

    Query namePinyinBoost2 = new BoostQuery(namePinyin2, 1f);
    // // 第一遍
    // Query namePinyinCapitalTermQuery = new TermQuery(new Term(
    // "namePinyinCapital", arg));
    // Query namePinyinCapitalTerm = new BoostQuery(
    // namePinyinCapitalTermQuery, 4.0f);
    //
    // // 第二遍
    // Query namePinyinCapitalPrefixQuery = new PrefixQuery(new Term(
    // "namePinyinCapital", arg));
    // Query namePinyinCapitalPrefix = new BoostQuery(
    // namePinyinCapitalPrefixQuery, 3.0f);
    Query namePinyinCapitalWildcardQuery1 =
        new WildcardQuery(new Term("namePinyinCapital", "*|" + arg + "|*"));
    Query namePinyinCapitalWildcard1 = new BoostQuery(namePinyinCapitalWildcardQuery1, 4.0f);
    // 第三遍
    Query namePinyinCapitalWildcardQuery =
        new WildcardQuery(new Term("namePinyinCapital", "*" + arg + "*"));
    Query namePinyinCapitalWildcard = new BoostQuery(namePinyinCapitalWildcardQuery, 2.0f);

    // Query namePinyinPrefixTerm = new PrefixQuery(
    // new Term("namePinyin", arg));

    // 多音字处理结束

    Query positionTerm = new WildcardQuery(new Term("position", "*" + arg + "*"));
    Query positionBoost = new BoostQuery(positionTerm, 0.5f);
    Query mobileTerm = new WildcardQuery(new Term("mobile", "*" + arg + "*"));
    Query emailTerm = new WildcardQuery(new Term("email", "*" + arg + "*"));
    Query homeTownTerm = new WildcardQuery(new Term("homeTown", "*" + arg + "*"));
    Query homeTownBoost = new BoostQuery(homeTownTerm, 0.5f);

    BooleanQuery booleanQuery = new BooleanQuery.Builder().add(userNameBoost, BooleanClause.Occur.SHOULD)
				.add(positionBoost, BooleanClause.Occur.SHOULD).add(mobileTerm, BooleanClause.Occur.SHOULD)
				.add(emailTerm, BooleanClause.Occur.SHOULD).add(homeTownBoost, BooleanClause.Occur.SHOULD)
				.add(namePinyinBoost, BooleanClause.Occur.SHOULD).add(namePinyinBoost2, BooleanClause.Occur.SHOULD)

				.add(namePinyinCapitalWildcard1, BooleanClause.Occur.SHOULD)
				.add(namePinyinCapitalWildcard, BooleanClause.Occur.SHOULD)
				.add(accountNumberTerm, BooleanClause.Occur.SHOULD).build();
    Sort sort = new Sort(new SortField("namePinyinCapital", Type.SCORE, false),
        new SortField("accountNumberLong", Type.LONG, false));
    TopDocs hits = null;

    hits = searcher.search(booleanQuery, num * eachPage, sort, true, true);
    ScoreDoc[] s = hits.scoreDocs;
    for (int i = ((num - 1) * eachPage); i < s.length; i++) {
      ScoreDoc sc = s[i];

      Document doc = searcher.doc(sc.doc);
      HmsStaff staff = new HmsStaff();

      if (doc.get("userName").indexOf(arg) != -1) {
        staff.setUserName(doc.get("userName").replaceAll(arg,
            "<span style='color: #11C1F3!important'>" + arg + "</span>"));
      } else {
        staff.setUserName(doc.getField("userName").stringValue());
      }
      if (doc.get("accountNumber").indexOf(arg) != -1) {
        staff.setAccountNumber(doc.get("accountNumber").replaceAll(arg,
            "<span style='color: #11C1F3!important'>" + arg + "</span>"));
      } else {
        staff.setAccountNumber(doc.getField("accountNumber").stringValue());
      }
      if (doc.get("email").indexOf(arg) != -1) {
        staff.setEmail(doc.get("email").replaceAll(arg,
            "<span style='color: #11C1F3!important'>" + arg + "</span>"));
      } else {
        staff.setEmail(doc.getField("email").stringValue());
      }
      if (doc.get("mobile").indexOf(arg) != -1) {
        staff.setMobile(doc.get("mobile").replaceAll(arg,
            "<span style='color: #11C1F3!important'>" + arg + "</span>"));
      } else {
        staff.setMobile(doc.getField("mobile").stringValue());
      }
      staff.setPosition(doc.getField("position").stringValue());

      staff.setHomeTown(doc.getField("homeTown").stringValue());

      staff.setEmpStatus(doc.getField("empStatus").stringValue());
      staff.setGenderId(Long.parseLong(doc.getField("gender").stringValue()));
      staff.setAvatar(doc.getField("avatar").stringValue());
      requestIdList.add(staff);
    }

    return StaffTransformer.changeListSimple(requestIdList);

  }



}
