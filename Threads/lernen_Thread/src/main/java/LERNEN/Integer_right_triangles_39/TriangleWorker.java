package LERNEN.Integer_right_triangles_39;

import java.util.*;
import java.util.concurrent.Callable;

public class TriangleWorker implements Callable<List<Set<Triangle>>> {
    private int startPerimeter;
    private int endPerimeter;

    public TriangleWorker(int startPerimeter, int endPerimeter) {
        this.startPerimeter = startPerimeter;
        this.endPerimeter = endPerimeter;
    }

    @Override
    public List<Set<Triangle>> call() throws Exception {
        List<Set<Triangle>> triangleList = new ArrayList<>();

        for (int perimeter = startPerimeter; perimeter <= endPerimeter; perimeter++) {
            Set<Triangle> triangles = new HashSet<>();

            for (int a = 1; a < perimeter/2; a++) {
                for (int b = 1; b < a; b++) {
                    double c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
                    if(c == (int) c && a+b+c == perimeter){
                        triangles.add(new Triangle(a, b, (int) c));
                    }
                }
            }

            triangleList.add(triangles);
        }

        return triangleList;
    }
}
