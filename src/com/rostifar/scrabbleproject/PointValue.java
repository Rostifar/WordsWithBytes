package com.rostifar.scrabbleproject;

/**
 * Created by Dad on 10/4/2015.
 */
public class PointValue {

    public int value;

    public PointValue(char aLetter) {
        value = getPointValueForLetter(aLetter);
    }

    @Override
    public String toString() {
        return "PointValue: " + value;
    }

    private int getPointValueForLetter(char aLetter) {

            int pointValue = 0;

            switch (aLetter) {

                case '_':
                    pointValue = 0;
                    break;

                case 'A':
                case 'E':
                case 'I':
                case 'L':
                case 'N':
                case 'O':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                    pointValue = 1;
                    break;

                case 'D':
                case 'G':
                    pointValue = 2;
                    break;

                case 'B':
                case 'C':
                case 'M':
                case 'P':
                    pointValue = 3;
                    break;

                case 'F':
                case 'H':
                case 'V':
                case 'W':
                case 'Y'://Switch statement

                    pointValue = 4;
                    break;

                case 'K':
                    pointValue = 5;
                    break;

                case 'J':
                case 'X':
                    pointValue = 8;
                    break;

                case 'Q':
                case 'Z':
                    pointValue = 10;
                    break;

                default:
                    break;

            }

            return pointValue;

        }


}
