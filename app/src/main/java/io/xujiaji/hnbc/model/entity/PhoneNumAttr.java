package io.xujiaji.hnbc.model.entity;

/**
 * Created by jiana on 16-11-15.
 */

public class PhoneNumAttr {

    /**
     * resultcode : 200
     * reason : Return Successd!
     * result : {"province":"四川","city":"泸州","areacode":"0830","zip":"646000","company":"中国电信","card":"中国电信"}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * province : 四川
     * city : 泸州
     * areacode : 0830
     * zip : 646000
     * company : 中国电信
     * card : 中国电信
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String province;
        private String city;
        private String areacode;
        private String zip;
        private String company;
        private String card;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", areacode='" + areacode + '\'' +
                    ", zip='" + zip + '\'' +
                    ", company='" + company + '\'' +
                    ", card='" + card + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PhoneNumAttr{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
