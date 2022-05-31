package com.example.carvajaltechnicaltest.service;

import com.example.carvajaltechnicaltest.dto.DnaDto;
import com.example.carvajaltechnicaltest.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DnaServiceImpl {

    private UserServiceImpl userService;

    public DnaDto dnaResults(UserDto user){
        DnaDto dnaDto = new DnaDto();
        String[] dna = user.getDna();
        dnaDto.setDna(dna);
        dnaDto.setDna(dna);
        dnaDto.setState(validateDna(dna));
        dnaDto.setIncidences(totalSequences(dna));

        return dnaDto;
    }


    public char[][] to2d(String[] dna) {
        char[][] dnaCuadrado = new char[dna.length][dna.length];
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[0].length(); j++) {
                //completa la matriz con el dna
                dnaCuadrado[i][j] = dna[i].toCharArray()[j];
            }
        }
        return dnaCuadrado;
    }

    public int findHorizontally(String[] dna) {
        char[][] matriz = to2d(dna);
        int row = matriz.length;
        int col = matriz[0].length;
        int repeatedValues = 4;
        int horizontalSequence = 0;

        for (int y = 0; y < row; y++) {
            int consecutivesLetterCounter = 0;
            //Itera por columna
            for (int x = 0; x < col - 1; x++) {
                if (matriz[y][x] == matriz[y][x + 1]) {
                    consecutivesLetterCounter++;
                } else {
                    consecutivesLetterCounter = 0;
                }
                if (consecutivesLetterCounter == repeatedValues - 1) {
                    horizontalSequence++;
                    
                }
            }
        }
        return horizontalSequence;
    }

    public int findVertically(String[] dna) {
        char[][] matriz = to2d(dna);
        int row = matriz.length;
        int col = matriz[0].length;
        int repeatedValues = 4;
        int verticalSequence = 0;


        //Itera por columna
        for (int x = 0; x < col; x++) {
            int consecutivesLetterCounter = 0;

            //Itera por fila
            for (int y = 0; y < row -1; y++){


                if (matriz[y][x] == matriz[y +1 ][x]) {

                    consecutivesLetterCounter++;
                } else {
                    consecutivesLetterCounter = 0;
                }
                if (consecutivesLetterCounter == repeatedValues -1) {
                    verticalSequence++;
                }
            }
        }
        return verticalSequence;

    }

    public int findDiagonally(String[] dna) {
        char[][] matriz = to2d(dna);
        int row = matriz.length;
        int col = matriz[0].length;
        int repeatedValues = 4;
        int diagonalSequence = 0;
        int consecutivesLetter = 0;

        for (int x = 0; x < col - 1; x++) {
            int y = x;
            if (y < row - 1) {
                if (matriz[y][x] == matriz[y + 1][x + 1]) {
                    consecutivesLetter++;

                } else {
                    consecutivesLetter = 0;
                }
                if (consecutivesLetter == repeatedValues - 1) {
                    diagonalSequence++;
                }
            }
        }

        for (int rightSideX = 1; rightSideX < col; rightSideX++) {
            consecutivesLetter = 0;
            for (int x = rightSideX; x < col - 1; x++) {

                int y = x - rightSideX;

                if (y < row - 1) {
                    if (matriz[y][x] == matriz[y + 1][x + 1]) {
                        consecutivesLetter++;

                    } else {
                        consecutivesLetter = 0;
                    }
                    if (consecutivesLetter == repeatedValues - 1) {
                        diagonalSequence++;
                    }

                }
            }
        }

        for (int rightSideY = 1; rightSideY < row; rightSideY++) {
            consecutivesLetter = 0;
            for (int y = rightSideY; y < row - 1; y++) {

                int x = y - rightSideY;

                if (x < col - 1) {
                    if (matriz[y][x] == matriz[y + 1][x + 1]) {
                        consecutivesLetter++;

                    } else {
                        consecutivesLetter = 0;
                    }
                    if (consecutivesLetter == repeatedValues - 1) {
                        diagonalSequence++;
                    }

                }
            }
        }
        return diagonalSequence;

    }

    public int totalSequences(String[] matriz) {
        int total1 = findHorizontally(matriz);
        int total2 = findVertically(matriz);
        int total3 = findDiagonally(matriz);
        int totalFinal = total1 + total2 + total3;

        return totalFinal;
    }

    public boolean validateDna(String[]dna){
        boolean validate = false;
        int totalFinal = totalSequences(dna);
        if(totalFinal >= 2){
            validate = true;
        }
        return validate;
    }



}
