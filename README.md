KalmanFilter
============

Testing Kalman Filter for GPS data

Dependiences
------------

- Guava

Input:
------

```bash
lon_lat.dat file
```

Adjusting accuracy:
----------

Adjust:
```bash
Constants.MIN_ACCURACY
```
to value which fits your needs.

Smaller the value is -> Kalman filter algorithm has less impact to the final data. 

with format:

    No | speed[m/s] | latitide | longitude | timestamp [millis]
    
    1 0.43785 17.11855577502637 51.03160054888576 1403701714000
    2 0.43785 17.11851547502637 51.03160074888576 1403701714000
    3 0.0 17.11855547502637 51.03160054888576 1403711033000
    4 0.31275 17.11853729350984 51.03159715421498 1403711034000
    .
    .
    .

Output:
-------

```bash
new_lon_lat.dat
```

Usage with [gnuplot]:
---------------------

Old chart:
```bash
plot "lon_lat.dat" using 3:4 w l
```
![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilter/master/charts/old.png)

New chart:
```bash
plot "new_lon_lat.dat" using 2:3 w l
```
![alt tag](https://raw.githubusercontent.com/Bresiu/KalmanFilter/master/charts/new.png)

[gnuplot]:http://www.gnuplot.info/
