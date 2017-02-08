package hmap.core.hms.system.domain;

/**
 * Created by Koma.Tshu on 2016/8/11.
 */

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "sys_prompts")
public class SysPrompt extends BaseDTO {
    private static final long serialVersionUID = 2856108923186548186L;
    @Id
    @GeneratedValue
    private Long promptId;
    private String promptCode;
    private String lang;
    private String description;

    public Long getPromptId() {
        return promptId;
    }

    public void setPromptId(Long promptId) {
        this.promptId = promptId;
    }

    public String getPromptCode() {
        return promptCode;
    }

    public void setPromptCode(String promptCode) {
        this.promptCode = promptCode;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
