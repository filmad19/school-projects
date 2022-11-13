package LERNEN.Integer_right_triangles_39;

import java.util.Arrays;

public class Triangle{
    private int[] sides = new int[3];

    public Triangle(int a, int b, int c) {
        this.sides[0] = a;
        this.sides[1] = b;
        this.sides[2] = c;
    }

    public int[] getSides() {
        return sides;
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

    @Override
    public String toString() {
        int perimeter = sides[0] + sides[1] + sides[2];

        return perimeter + " - " + Arrays.toString(sides);
    }
}
