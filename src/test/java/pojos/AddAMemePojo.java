package pojos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddAMemePojo {

    private String name;
    private String tags;
    private String image;
    private String topText;
    private String bottomText;
    private String detail;
    private String thumb;
    private String rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "AddAMemePojo{" +
                "name='" + name + '\'' +
                ", tags='" + tags + '\'' +
                ", image='" + image + '\'' +
                ", topText='" + topText + '\'' +
                ", bottomText='" + bottomText + '\'' +
                ", detail='" + detail + '\'' +
                ", thumb='" + thumb + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
