package at.kaindorf.Theorie_Thread.Integer_Right_Triangle_39;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class TriangleWorker implements Callable<Set<Triangle>> {
    private int perimeter;

    public TriangleWorker(int perimeter) {
        this.perimeter = perimeter;
    }

    @Override
    public Set<Triangle> call() throws Exception {
        Set<Triangle> trianglesFound = new HashSet<>();

        for(int a = 1;a < perimeter/2;a++){
            for(int b = 1;b < perimeter/2;b++){
                for(int c = 1;c < perimeter/2;c++){
//                    ob RECHTWINKELIG und UMFANG richtig
                    if((a*a + b*b == c*c) && (a+b+c == perimeter)){
                        trianglesFound.add(new Triangle(a, b, c));
                    }
                }
            }
        }

        return trianglesFound;
    }
}
