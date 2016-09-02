package blkxltng.com.sdahymnal;

/**
 * Created by firej on 8/30/2016.
 */
public class Hymns {

    private int number;
    private String title;
    private String section;
    private String subSection;
    private int firstHymn;
    private int lastHymn;

    public int getFirstHymn() {
        return firstHymn;
    }

    public void setFirstHymn(int firstHymn) {
        this.firstHymn = firstHymn;
    }

    public int getLastHymn() {
        return lastHymn;
    }

    public void setLastHymn(int lastHymn) {
        this.lastHymn = lastHymn;
    }

    private String image;
    private int id;
    private String verse1;
    private String verse2;
    private String verse3;
    private String verse4;
    private String verse5;
    private String verse6;
    private String refrain;

    public String getRefrain() {
        return refrain;
    }

    public void setRefrain(String refrain) {
        this.refrain = refrain;
    }

    public String getVerse1() {
        return verse1;
    }

    public void setVerse1(String verse1) {
        this.verse1 = verse1;
    }

    public String getVerse2() {
        return verse2;
    }

    public void setVerse2(String verse2) {
        this.verse2 = verse2;
    }

    public String getVerse3() {
        return verse3;
    }

    public void setVerse3(String verse3) {
        this.verse3 = verse3;
    }

    public String getVerse4() {
        return verse4;
    }

    public void setVerse4(String verse4) {
        this.verse4 = verse4;
    }

    public String getVerse5() {
        return verse5;
    }

    public void setVerse5(String verse5) {
        this.verse5 = verse5;
    }

    public String getVerse6() {
        return verse6;
    }

    public void setVerse6(String verse6) {
        this.verse6 = verse6;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Hymns() {

    }

    public Hymns(String section) {
        this.section = section;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }
}
