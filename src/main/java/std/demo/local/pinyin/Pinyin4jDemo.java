package std.demo.local.pinyin;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jDemo {

	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER); 
        
        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('行')));
        System.out.println(Arrays.toString(PinyinHelper.toTongyongPinyinStringArray('行')));
        System.out.println(Arrays.toString(PinyinHelper.toYalePinyinStringArray('行')));
        
        
        String pinyin = PinyinHelper.toHanYuPinyinString("呵呵哈哈呵", defaultFormat, "_", true);
        System.err.println(pinyin);
	}
}
