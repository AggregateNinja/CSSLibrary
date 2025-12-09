
package EPOS;



/**
 * @date:   Mar 20, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: BulkMasterLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import java.util.Date;



public class BulkMasterLine {
    
    private String arnum;
    private String lname;
    private String fname;
    private String mname;
    private String addr;
    private String city;
    private String state;
    private String zip;
    private String hphone;
    private String wphone;
    private Date dob;
    private String medicare;
    private String medicaid1;
    private String medicaid2;
    private String group1;
    private String agree1;
    private String group2;
    private String agree2;
    private int sinsurnum;
    private int insnum;
    private int age;
    private String age_units;
    private int clinum;
    private int docnum;
    private String bill_type;
    private Date collect;
    private int coll_flag;
    private String sex;
    private Date ldos;
    private String employer;
    private String snfid;
    private Date au_exp;
    private int subs_arnum;
    private String relation;
    private int at0;
    private int at1;
    private int at2;
    private int at3;
    private int at4;
    private int at5;
    private int at6;
    private int at7;
    private Date last_edit;
    private Date au_beg;
    private int autest0;
    private int autest1;
    private int autest2;
    private int autest3;
    private int autest4;
    private int autest5;
    private int autest6;
    private int autest7;
    private int autest8;
    private int autest9;
    private int autest10;
    private int autest11;
    private int autest12;
    private int autest13;
    private int autest14;
    private int autest15;
    private String def_diag;
    private int ssn;
    private String patid;
    
    public BulkMasterLine(){}

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    
    public String getCity(){
        return city;
    }
    
    public void setCity(String city){
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAge_units() {
        return age_units;
    }

    public void setAge_units(String age_units) {
        this.age_units = age_units;
    }

    public String getAgree1() {
        return agree1;
    }

    public void setAgree1(String agree1) {
        this.agree1 = agree1;
    }

    public String getAgree2() {
        return agree2;
    }

    public void setAgree2(String agree2) {
        this.agree2 = agree2;
    }

    public String getArnum() {
        return arnum;
    }

    public void setArnum(String arnum) {
        this.arnum = arnum;
    }

    public int getAt0() {
        return at0;
    }

    public void setAt0(int at0) {
        this.at0 = at0;
    }

    public int getAt1() {
        return at1;
    }

    public void setAt1(int at1) {
        this.at1 = at1;
    }

    public int getAt2() {
        return at2;
    }

    public void setAt2(int at2) {
        this.at2 = at2;
    }

    public int getAt3() {
        return at3;
    }

    public void setAt3(int at3) {
        this.at3 = at3;
    }

    public int getAt4() {
        return at4;
    }

    public void setAt4(int at4) {
        this.at4 = at4;
    }

    public int getAt5() {
        return at5;
    }

    public void setAt5(int at5) {
        this.at5 = at5;
    }

    public int getAt6() {
        return at6;
    }

    public void setAt6(int at6) {
        this.at6 = at6;
    }

    public int getAt7() {
        return at7;
    }

    public void setAt7(int at7) {
        this.at7 = at7;
    }

    public Date getAu_beg() {
        return au_beg;
    }

    public void setAu_beg(Date au_beg) {
        this.au_beg = au_beg;
    }

    public Date getAu_exp() {
        return au_exp;
    }

    public void setAu_exp(Date au_exp) {
        this.au_exp = au_exp;
    }

    public int getAutest0() {
        return autest0;
    }

    public void setAutest0(int autest0) {
        this.autest0 = autest0;
    }

    public int getAutest1() {
        return autest1;
    }

    public void setAutest1(int autest1) {
        this.autest1 = autest1;
    }

    public int getAutest10() {
        return autest10;
    }

    public void setAutest10(int autest10) {
        this.autest10 = autest10;
    }

    public int getAutest11() {
        return autest11;
    }

    public void setAutest11(int autest11) {
        this.autest11 = autest11;
    }

    public int getAutest12() {
        return autest12;
    }

    public void setAutest12(int autest12) {
        this.autest12 = autest12;
    }

    public int getAutest13() {
        return autest13;
    }

    public void setAutest13(int autest13) {
        this.autest13 = autest13;
    }

    public int getAutest14() {
        return autest14;
    }

    public void setAutest14(int autest14) {
        this.autest14 = autest14;
    }

    public int getAutest15() {
        return autest15;
    }

    public void setAutest15(int autest15) {
        this.autest15 = autest15;
    }

    public int getAutest2() {
        return autest2;
    }

    public void setAutest2(int autest2) {
        this.autest2 = autest2;
    }

    public int getAutest3() {
        return autest3;
    }

    public void setAutest3(int autest3) {
        this.autest3 = autest3;
    }

    public int getAutest4() {
        return autest4;
    }

    public void setAutest4(int autest4) {
        this.autest4 = autest4;
    }

    public int getAutest5() {
        return autest5;
    }

    public void setAutest5(int autest5) {
        this.autest5 = autest5;
    }

    public int getAutest6() {
        return autest6;
    }

    public void setAutest6(int autest6) {
        this.autest6 = autest6;
    }

    public int getAutest7() {
        return autest7;
    }

    public void setAutest7(int autest7) {
        this.autest7 = autest7;
    }

    public int getAutest8() {
        return autest8;
    }

    public void setAutest8(int autest8) {
        this.autest8 = autest8;
    }

    public int getAutest9() {
        return autest9;
    }

    public void setAutest9(int autest9) {
        this.autest9 = autest9;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public int getClinum() {
        return clinum;
    }

    public void setClinum(int clinum) {
        this.clinum = clinum;
    }

    public int getColl_flag() {
        return coll_flag;
    }

    public void setColl_flag(int coll_flag) {
        this.coll_flag = coll_flag;
    }

    public Date getCollect() {
        return collect;
    }

    public void setCollect(Date collect) {
        this.collect = collect;
    }

    public String getDef_diag() {
        return def_diag;
    }

    public void setDef_diag(String def_diag) {
        this.def_diag = def_diag;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getDocnum() {
        return docnum;
    }

    public void setDocnum(int docnum) {
        this.docnum = docnum;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }

    public String getGroup2() {
        return group2;
    }

    public void setGroup2(String group2) {
        this.group2 = group2;
    }

    public String getHphone() {
        return hphone;
    }

    public void setHphone(String hphone) {
        this.hphone = hphone;
    }

    public int getInsnum() {
        return insnum;
    }

    public void setInsnum(int insnum) {
        this.insnum = insnum;
    }

    public Date getLast_edit() {
        return last_edit;
    }

    public void setLast_edit(Date last_edit) {
        this.last_edit = last_edit;
    }

    public Date getLdos() {
        return ldos;
    }

    public void setLdos(Date ldos) {
        this.ldos = ldos;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMedicare() {
        return medicare;
    }

    public void setMedicare(String medicare) {
        this.medicare = medicare;
    }

    public String getMedicaid1() {
        return medicaid1;
    }

    public void setMedicaid1(String medicaid1) {
        this.medicaid1 = medicaid1;
    }

    public String getMedicaid2() {
        return medicaid2;
    }

    public void setMedicaid2(String medicaid2) {
        this.medicaid2 = medicaid2;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getPatid() {
        return patid;
    }

    public void setPatid(String patid) {
        this.patid = patid;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getSinsurnum() {
        return sinsurnum;
    }

    public void setSinsurnum(int sinsurnum) {
        this.sinsurnum = sinsurnum;
    }

    public String getSnfid() {
        return snfid;
    }

    public void setSnfid(String snfid) {
        this.snfid = snfid;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSubs_arnum() {
        return subs_arnum;
    }

    public void setSubs_arnum(int subs_arnum) {
        this.subs_arnum = subs_arnum;
    }

    public String getWphone() {
        return wphone;
    }

    public void setWphone(String wphone) {
        this.wphone = wphone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    
}
