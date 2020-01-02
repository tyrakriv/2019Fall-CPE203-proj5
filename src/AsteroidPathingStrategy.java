import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class AsteroidPathingStrategy
        implements PathingStrategy {

    private static final Random rand = new Random();

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        /* Does not check withinReach.  Since only a single step is taken
         * on each call, the caller will need to check if the destination
         * has been reached.
         */

        List<Point> points = potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)
                )
                .collect(Collectors.toList());
        List<Point> finalP = new ArrayList<>();
        System.out.println(points.size());
        if (points.size() == 0){
            finalP.add(start);
        }
        else if (points.size() == 1){
            finalP.add(start);
        }
        else{
            finalP.add(points.get(rand.nextInt(points.size() - 1)));
        }
        return finalP;
    }
}