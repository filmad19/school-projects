package at.kaindorf.Theorie_Thread.Integer_Right_Triangle_39;

import java.util.Arrays;

public class Triangle{
    private int[] sides;

    public Triangle(int a, int b, int c) {
        sides = new int[] {a, b, c};
        Arrays.sort(sides);
    }

    public int[] getSides() {
        return sides;
    }

    @Override
    public String toString() {
        int perimeter = sides[0] + sides[1] + sides[2];

        return "\n" + perimeter + " - " + Arrays.toString(sides);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Arrays.equals(sides, triangle.sides);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(sides);
    }
}
