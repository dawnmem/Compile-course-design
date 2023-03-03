package cPreprocessing;

import java.util.ArrayList;
public class Test {
    public static void main(String[] args){
        System.out.print(new Solution().lengthOfLongestSubstring("ssssss57890-s74567890-198")+"\n");
    }
}
class Solution{
    public int lengthOfLongestSubstring(String s) {
        int i=0,j=0;
        ArrayList<Character> objectName =new ArrayList<>();
        int count=0;
        for(int k=0;k<s.length();k++){
            if(!objectName.contains(s.charAt(k))){
                objectName.add(s.charAt(k));
                j++;
            }
            else{
                if(count<j-i){
                    count=j-i;
                }
                int flag=objectName.indexOf(s.charAt(k));
                objectName.add(s.charAt(k));
                while(flag>=0){
                    objectName.remove(0);
                    flag--;
                }
                i=0;
                j=objectName.size();
            }
        }
        if(count<j-i){
            count=j-i;
        }
        return count;
    }
}
