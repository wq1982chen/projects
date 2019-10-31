package my.learn;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
	
	
	
	public static byte[] decimal‎2Bcd(String decimal) throws UnsupportedEncodingException {  
        int len = decimal.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            decimal = "0" + decimal;  
            len = decimal.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = decimal.getBytes();  
        System.out.printf("abt-----[%s]----[%s]->\n" ,decimal,Arrays.toString(abt));
        int j, k;  
        for (int p = 0; p < decimal.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }  
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		//BCD压缩
		
		 //byte[] b2=decimal‎2Bcd("0486FA6C5D3D2CDA"); //byte[]
		 byte[] b2=decimal‎2Bcd("0486FA6C5D3D2CDA"); //byte[]
		// b2=decimal‎2Bcd("1006008");
//		 System.out.println("BCD压缩后的字节数组："+Arrays.toString(b2));
		
		// -------------------------------------------------------------
		 String a=new String(b2,"ISO-8859-1"); 
		 
		 //------------------------------------------------------------
//		 System.out.println("BCD压缩后的字符串："+a); 
		 byte[] b1=a.getBytes("ISO-8859-1"); 
//		 System.out.println(Arrays.toString(b1));
		 
		 StringBuffer temp = new StringBuffer(b1.length * 2); 
		 for (int i = 0; i < b1.length; i++) { 
			int xx1=(b1[i] & 0xf0) >>> 4; 
            int xx2=b1[i] & 0x0f;
            temp.append(Integer.toHexString(xx1)); 
            temp.append(Integer.toHexString(xx2));
		 }
		 
		 System.out.println("解压后："+temp);
		 System.out.println("本机的默认编码====="+System.getProperty("file.encoding"));
//		11100101
//		byte[] b2= {65,65,30};
//		String s = new String(b2);
//		System.out.println(s);
//		System.out.println(Arrays.toString("0".getBytes("ASCII")));
		 
		 String s = "1";
		 s.intern();
	}
}
