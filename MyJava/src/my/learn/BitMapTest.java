package my.learn;

public class BitMapTest {

	/**
     * ����bitmap����
     */
    public byte[] create(int n){
        byte[] bits = new byte[getIndex(n) + 1];
        
        for(int i = 0; i < n; i++){
            add(bits, i);
        }
        
        System.out.println(contains(bits, 11));
        
        int index = 1;
        for(byte bit : bits){
            System.out.println("-------" + index++ + "-------");
            showByte(bit);

        }
        
        return bits;
    }
    
    /**
     * ���ָ�����֣�num����bitmap�е�ֵ��������Ѿ����ֹ�<br/>
     * ��1����position���Ǹ�λ����Ȼ����1��Ȼ�����ǰ��������|���������Ǹ�λ�þ��滻��1��
     * @param bits
     * @param num
     */
    public void add(byte[] bits, int num){
        bits[getIndex(num)] |= 1 << getPosition(num);
    }
    
    /**
     * �ж�ָ������num�Ƿ����<br/>
     * �Ƚ�1����position���Ǹ�λ����Ȼ����1��Ȼ�����ǰ��������&���ж��Ƿ�Ϊ0����
     * @param bits
     * @param num
     * @return
     */
    public boolean contains(byte[] bits, int num){
        return (bits[getIndex(num)] & 1 << getPosition(num)) != 0;
    }
    
    /**
     * num/8�õ�byte[]��index
     * @param num
     * @return
     */
    public int getIndex(int num){
        return num >> 3;
    }
    
    /**
     * num%8�õ���byte[index]��λ��
     * @param num
     * @return
     */
    public int getPosition(int num){
        return num & 0x07;
    }
    
    /**
     * ����ĳһ���ֶ�Ӧ��bitmap�е�ֵ<br/>
     * ��1�������ƣ�Ȼ��ȡ���������byte[index]���������
     * @param bits
     * @param num
     */
    public void clear(byte[] bits, int num){
        bits[getIndex(num)] &= ~(1 << getPosition(num));
    }
    
    /**
     * ��ӡbyte���͵ı���<br/>
     * ��byteת��Ϊһ������Ϊ8��byte���飬����ÿ��ֵ����bit
     */
    
    public void showByte(byte b){
        byte[] array = new byte[8];
        for(int i = 7; i >= 0; i--){
            array[i] = (byte)(b & 1);
            b = (byte)(b >> 1);
        }
        
        for (byte b1 : array) {
            System.out.print(b1);
            System.out.print(" ");
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        int n = 100;
        new BitMapTest().create(n);
    }
}
