package at.kaindorf.Theorie_Thread.ÜBUNGEN.XOR_decryption_59;

import java.util.concurrent.Callable;

public class DecryptionWorker implements Callable<String> {
    //    static files
    private static String[] commonEnglishWords;
    private static int[] file;

    //    char the thread starts with
    private char first;

    public DecryptionWorker(char firstChar) {
        this.first = firstChar;
    }

    public static void setCommonEnglishWords(String[] commonEnglishWords) {
        DecryptionWorker.commonEnglishWords = commonEnglishWords;
    }

    public static void setFile(int[] file) {
        DecryptionWorker.file = file;
    }

    @Override
    public String call() throws Exception {
        for(char second = 'a'; second <= 'z'; second++){
            for(char third = 'a'; third <= 'z'; third++){ //go through any combination
                String resultText = "";
                int resultAscii = 0;

                for(int i = 0;i < file.length;i+=3){
                    if(i > file.length-3){
                        resultText += (char) (file[i] ^ first);
                        resultAscii += (file[i] ^ first);
                        if(i > file.length-2){
                            resultText += (char) (file[i+1] ^ second);
                            resultAscii += (file[i] ^ second);
                            break;
                        }
                        resultText += (char) (file[i+1] ^ second);
                        resultAscii += (file[i] ^ second);
                        break;
                    }

                    resultText += (char) (file[i] ^ first);
                    resultText += (char) (file[i+1] ^ second);
                    resultText += (char) (file[i+2] ^ third);
                    resultAscii = resultAscii + (file[i] ^ first) + (file[i+1] ^ second) + (file[i+2] ^ third);


                }

                int cnt = 0;

                for(int j = 0; j < commonEnglishWords.length; j++){
                    if(resultText.contains(commonEnglishWords[j])) {
                        cnt++;
                    }
                }

                if(cnt >= 4){
                    resultText = resultText.replace(" ", "\n");
                    System.out.println(resultAscii);
                    return resultText;
                }
            }
        }
        throw new Exception("result not found");
    }
}
