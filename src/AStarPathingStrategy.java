import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy {
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Point> path = new LinkedList<>();
        HashMap<Point, Node> openH = new HashMap<>();
        HashMap<Point, Node> closedH = new HashMap<>();
        List<Node> openA = new ArrayList<>();

        Node c = new Node(start, null);
        openA.add(c);
        openH.put(start, c);

        while (!withinReach.test(end, c.vertex)) {
            if (openA.isEmpty())
            {
                return path;
            }
            openA.sort(Comparator.comparingDouble(Node::getF));
            c = openA.get(0);
            openA.remove(c);
            c.setG(start);
            c.setH(end);
            c.setF();
            closedH.put(c.vertex, c);
            openH.remove(c.vertex);

            List<Point> neighbors = potentialNeighbors.apply(c.vertex)
                    .filter(canPassThrough)
                    .filter(pt ->
                            !pt.equals(start)
                                    && !closedH.containsKey(pt))
                    .collect(Collectors.toList());
            for (Point n : neighbors) {
                Node nn = new Node(n, c);
                nn.setG(start);
                nn.setH(end);
                nn.setF();
                if (openH.containsKey(n) && openH.get(n).g > nn.g)
                {
                    openA.remove(openH.get(n));
                    openA.add(nn);
                    openH.remove(n);
                    openH.put(n, nn);
                }
                else if (!openH.containsKey(n)) {
                    openH.put(n, nn);
                    openA.add(nn);
                }
            }
        }

        while(c.vertex != start)
        {
            path.add(c.vertex);
            c = c.previous;
        }
        Collections.reverse(path);
        return path;
    }

    private class Node {
        private Point vertex;
        private Node previous;
        private double f;
        private double g;
        private double h;

        public Node(Point vertex, Node previous) {
            this.vertex = vertex;
            this.previous = previous;
            this.f = this.g + this.h;
            this.g = 0;
            this.h = 0;
        }


        public double getF() {
            return f;
        }

        public void setF() {
            this.f = this.g + this.h;
        }

        public void setG(Point start) {
            if (previous != null)
            {
                if (vertex.x+1 == previous.vertex.x && vertex.y == previous.vertex.y ||
                        vertex.x-1 == previous.vertex.x && vertex.y == previous.vertex.y ||
                        vertex.x == previous.vertex.x && vertex.y+1 == previous.vertex.y ||
                        vertex.x == previous.vertex.x && vertex.y-1 == previous.vertex.y)
                {
                    this.g = previous.g + 1;

                }
                else {
                    this.g = previous.g + 1.4;
                }
            }
            else
            {
                this.g = Math.abs(this.vertex.x - start.x) + Math.abs(this.vertex.y - start.y);
            }
        }

        public void setH(Point end) {
            this.h = Math.abs(this.vertex.x - end.x) + Math.abs(this.vertex.y - end.y);
        }

        public void setPrevious(Node p) {
            this.previous = p;
        }
    }
}
