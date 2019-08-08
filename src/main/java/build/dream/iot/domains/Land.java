package build.dream.iot.domains;

import java.math.BigDecimal;
import java.util.Date;

public class Land {
    /**
     * 所在城市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

    /**
     * 土地编号
     */
    private String landNumber;

    /**
     * 用地面积
     */
    private BigDecimal landArea;

    /**
     * 土地位置
     */
    private String landLocation;

    /**
     * 规划用途
     */
    private String purpose;

    /**
     * 出让方式
     */
    private String transferMode;

    /**
     * 出让价格
     */
    private BigDecimal transferPrice;

    /**
     * 开始日期
     */
    private String startYear;

    /**
     * 结束日期
     */
    private String endYear;

    /**
     * 成交日期
     */
    private String dealYear;

    /**
     * 成交价格
     */
    private BigDecimal dealPrice;

    /**
     * 成交人
     */
    private String dealer;

    /**
     * 溢价率
     */
    private BigDecimal premiumRate;

    /**
     * 容积率
     */
    private String volumeRatio;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(String landNumber) {
        this.landNumber = landNumber;
    }

    public BigDecimal getLandArea() {
        return landArea;
    }

    public void setLandArea(BigDecimal landArea) {
        this.landArea = landArea;
    }

    public String getLandLocation() {
        return landLocation;
    }

    public void setLandLocation(String landLocation) {
        this.landLocation = landLocation;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public BigDecimal getTransferPrice() {
        return transferPrice;
    }

    public void setTransferPrice(BigDecimal transferPrice) {
        this.transferPrice = transferPrice;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getDealYear() {
        return dealYear;
    }

    public void setDealYear(String dealYear) {
        this.dealYear = dealYear;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public BigDecimal getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(BigDecimal premiumRate) {
        this.premiumRate = premiumRate;
    }

    public String getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(String volumeRatio) {
        this.volumeRatio = volumeRatio;
    }
}
