/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.model;

import java.util.Collections;

/**
 *
 * @author liam
 */
public class ChangeMakingCode {
    
    private final Integer[] coins = {10, 50, 20, 5, 1, 2, 20, 50, 10, 5};
    private final Integer[] solution = new Integer[10];

    public Integer[] getSolution() {
        return solution;
    }

    public Integer[] getCoins() {
        return coins;
    }
    
    public Integer getCoin(Integer i) {
        return coins[i];
    }
    
    public Integer getSolution(Integer i) {
        return solution[i];
    }
    
    public void changeMaking(Integer change) {
        Integer j = 0;
        java.util.Arrays.sort(coins, Collections.reverseOrder());
        for(Integer i = 0; i < coins.length; i++) {
            if(coins[i] <= change) {
                solution[j] = coins[i];
                change = change - coins[i];
                j = j + 1;
            }
        }
        java.util.Arrays.fill(solution, j, solution.length, 0);
        //System.out.println(java.util.Arrays.toString(solution));
    }
}
