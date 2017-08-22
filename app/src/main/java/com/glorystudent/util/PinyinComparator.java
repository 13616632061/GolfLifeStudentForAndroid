package com.glorystudent.util;

import com.glorystudent.entity.TeamMemberEntity;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Comparator;

/**
 * Created by Gavin.J on 2017/5/17.
 */

public class PinyinComparator implements Comparator<TeamMemberEntity.TeamUserListBean> {
    @Override
    public int compare(TeamMemberEntity.TeamUserListBean o1, TeamMemberEntity.TeamUserListBean o2) {
        char c1 = o1.getName().charAt(0);
        char c2 = o2.getName().charAt(0);
        return concatPinyinStringArray(PinyinHelper.toHanyuPinyinStringArray(c1))
                .compareTo(concatPinyinStringArray(PinyinHelper.toHanyuPinyinStringArray(c2)));
    }

    private String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinSbf = new StringBuffer();
        if ((pinyinArray != null) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinSbf.append(pinyinArray[i]);
            }
        }
        return pinyinSbf.toString();
    }
}
