
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dimitar
 */
public class Main {

    public static void main(String[] args) {

        PersistentVector<Integer> vector = new PersistentVector<>();
        for (int i = 0; i < 10; i++) {
            vector.append(i);
            System.out.println(vector.depth);
            System.out.println(vector.root);
            System.out.println(vector.lookUp());
        }
//        for (int i = 32; i < 64; i++) {
//            vector.append(i);
//        }

        vector.append(23);
        System.out.println(vector.size());
    }

}
