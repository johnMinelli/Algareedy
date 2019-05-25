/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.model;

/**
 *
 * @author liam
 */
public class ChangeMakingCode {
    
    private Integer[] coins = {10, 50, 20, 5, 1, 2, 20, 50, 10, 5};
    private Integer[] solution = new Integer[10];
    
    public void changeMaking(Integer change) {
        Integer j = 0;
        java.util.Arrays.sort(coins);
        for(Integer i = coins.length -1; i > 0; i--) {
            if(coins[i] <= change) {
                solution[j] = coins[i];
                change = change - coins[i];
                j = j + 1;
            }
        }
        java.util.Arrays.fill(solution, j, solution.length, 0);
        System.out.println(java.util.Arrays.toString(solution));    
    }
}
