/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.domain Date:2016/11/11 0011 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.domain;

import com.hand.hap.system.dto.BaseDTO;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "hms_contact_tag")
@Document(indexName = "hms_contact_tag", type = "hms_contact_tag", shards = 1, replicas = 0,
    refreshInterval = "-1", indexStoreType = "fs")
public class HmsContactTag extends BaseDTO {
  @Id
  @GeneratedValue(generator = "UUID")
  @org.springframework.data.annotation.Id
  @Field(type = FieldType.String, store = true,index = FieldIndex.not_analyzed)
  private String id;
  @Column
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String tagName;
  @Column
  private String tagStatus;
  @Column
  private String tagOwner;
  @Column
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
  private String tagPinyin;
  @Column
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
  private String tagPinyinCapital;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTagStatus() {
    return tagStatus;
  }

  public void setTagStatus(String tagStatus) {
    this.tagStatus = tagStatus;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String getTagOwner() {
    return tagOwner;
  }

  public void setTagOwner(String tagOwner) {
    this.tagOwner = tagOwner;
  }

  public String getTagPinyin() {
    return tagPinyin;
  }

  public void setTagPinyin(String tagPinyin) {
    this.tagPinyin = tagPinyin;
  }

  public String getTagPinyinCapital() {
    return tagPinyinCapital;
  }

  public void setTagPinyinCapital(String tagPinyinCapital) {
    this.tagPinyinCapital = tagPinyinCapital;
  }
}
