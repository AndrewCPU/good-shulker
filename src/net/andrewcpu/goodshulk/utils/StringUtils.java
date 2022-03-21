package net.andrewcpu.goodshulk.utils;

import org.bukkit.ChatColor;

public class StringUtils {
    public static int countBefore(String s, String target){
        int targetCount = 0;
        for(int i =0; i<s.length(); i++){
            if(s.charAt(i) == target.charAt(0)){
                targetCount++;
            }
        }
        return targetCount;
    }

    public static String parseCommand(String command, ChatColor defaultColor){
        String output = "";
        String[] chars = command.split("");
        for(String s : chars){
            if(s.equals("[")){
                output += defaultColor + s + ChatColor.YELLOW;
            }
            else if(s.equals("]")){
                output += defaultColor + s;
            }
            else if(s.equals("|")){
                output += ChatColor.GRAY + s + ChatColor.YELLOW;
            }
            else if(s.equals("\"")){
                if(countBefore(output, "\"") % 2 == 0){
                    output += defaultColor + s + ChatColor.LIGHT_PURPLE;
                }
                else{
                    output += defaultColor + s;
                }
            }
            else if(s.equals(" ")){
                int countBefore = countBefore(output, " ");
                if(countBefore == 0){
                    output += s + ChatColor.GREEN;
                }
                else if(countBefore == 1){
                    output += s + ChatColor.AQUA;
                }
                else{
                    if(countBefore(output, "\"") % 2 == 0 ){
                        if(countBefore(output, "[")  == countBefore(output, "]") ){
                            output += s + defaultColor;
                        }
                        else{
                            output += s + ChatColor.YELLOW;
                        }
                    }
                    else{
                        output += s + ChatColor.LIGHT_PURPLE;
                    }
                }

            }
            else{
                output += s;
            }
        }
        return output;
    }
}
