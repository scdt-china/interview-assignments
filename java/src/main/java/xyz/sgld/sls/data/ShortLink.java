package xyz.sgld.sls.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import xyz.sgld.sls.util.NumberUtil;

import javax.persistence.*;
import java.util.Objects;

@ApiModel("短链接数据")
@Entity(name = "short_links")
public class ShortLink {

    /**
     * ID of ShortLink
     */
    @ApiModelProperty("链接ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonInclude
    @ApiModelProperty("短链接字段")
    @Transient
    private String shortLink;


    /**
     * Short link
     */
    @JsonIgnore
    private String hash;
    /**
     * Origin link
     */
    @Lob
    @ApiModelProperty("原始链接内容")
    private String originLink;

    /**
     * Set id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Get Id
     */
    public long getId() {
        return id;
    }


    /**
     * get short link
     */
    public String getHash() {
        return hash;
    }

    /**
     * set short link
     */
    public void setHash(String shortLink) {
        this.hash = shortLink;
    }

    /**
     * get origin link
     */
    public String getOriginLink() {
        return originLink;
    }

    /**
     * set origin link
     */
    public void setOriginLink(String originLink) {
        this.originLink = originLink;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortLink)) {
            return false;
        } else {
            ShortLink shortLink = (ShortLink) obj;
            if (shortLink.id == getId() && shortLink.getHash().equals(getHash()) &&
                    shortLink.getOriginLink().equals(getOriginLink())) {
                return true;
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.hash, this.originLink);
    }

    @Override
    public String toString() {
        return "ShortLink{ id=" + id + ",hash=" + hash + ",originLink=" + originLink + ",shortLink=" + shortLink + "}";
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

}
