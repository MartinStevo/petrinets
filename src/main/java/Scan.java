import Exceptions.WrongInputException;

import java.util.Scanner;

public class Scan {

    public Long scanId() throws WrongInputException {
        Scanner sc = new Scanner(System.in);
        Long id;
        try {
            id = sc.nextLong();
        } catch (Exception e) {
            throw new WrongInputException();
        }
        return id;
    }
}
