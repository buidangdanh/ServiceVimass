package vn.vimass.service.entity;

public class ObjectgetThietBiVPass {
    public String user ;
    public int perNum ; // 1,2,3,4 neu la the V
    public String mcID ;
    public long currentTime ;
    public String cks ; // md5: "Y99JAuGfmYaBYYyycsLy26" + mcID + currentTime;

    public String deviceID ; // neu truyen chi lay theo deviceID
    public int typeDevice ; // neu deviceID = null thi loc theo typeDevice.  truyen 0: ALL 	| 	typeDevice = 1|2 : V1,V2,..    |   typeDevice = 3,4 : I1,I2,...		| typeDevice = 5,6,7,8 : A1,A2,..

}
