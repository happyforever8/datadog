//给一个list/给一个list 里面有坐标，按间隙补齐缺失坐标,  点和点之间是直线连接，缺失的点也必须在直线上
//        for example, interval=5, interpolate missing point at x-coordinate with incremental of 5 (e.g. (0,y1), (5,y2), (10,y3)....
//        input = [(0,10), (10,10),(20, -10)]
//        output = [(0,10), (5,20),(10,10),(15,0) ,(20,-10)]
//        (5,20) 在直线(0,10)-(10,10)上, (15,0)在直线(10,10)-(20,-10)上

import java.util.ArrayList;
import java.util.List;

//The time complexity is O(n * max_xDiff), where n is the number of input points
//        and max_xDiff is the maximum difference between x-coordinates of consecutive points.
//The space complexity is primarily determined by the interpolatedPoints list, 
//        which could have up to n * interval interpolated points in the worst case.
//

// 将 x 替换为 x1 + 5，即新的横坐标。
// 具体计算步骤如下：

// 使用点斜式方程 y - y1 = m(x - x1) 得到 y - y1 = m(x1 + 5 - x1)。
// 化简方程，得到 y - y1 = 5m。
// 求解出 y，得到 y = y1 + 5m。
public class Interpolation {
    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static List<Point> interpolate(List<Point> points, int interval) {
        List<Point> interpolatedPoints = new ArrayList<>();

        if (points == null || points.isEmpty()) {
            return interpolatedPoints;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1);

            interpolatedPoints.add(current);

            int xDiff = next.x - current.x;
            int yDiff = next.y - current.y;

            for (int j = interval; j < xDiff; j += interval) {
                int interpolatedX = current.x + j;
                int interpolatedY = current.y + yDiff / xDiff * j;
                interpolatedPoints.add(new Point(interpolatedX, interpolatedY));
            }
        }

        interpolatedPoints.add(points.get(points.size() - 1));

        return interpolatedPoints;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(5, 100));
        points.add(new Point(15, 300));
        points.add(new Point(30, 150));

        int interval = 5;
        List<Point> interpolatedPoints = interpolate(points, interval);

        for (Point point : interpolatedPoints) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }
}
