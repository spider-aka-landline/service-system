set terminal postscript eps enhanced
set output "~/average.ps"
set encoding koi8r
set xlabel "Параметр регуляризациии {/Symbol a}, 10^{-n}" font "Helvetica,18"
set ylabel "Величина СКО, отн.ед" font "Helvetica,18"
set yrange [0:1]
set style line 1 lt 1 pt 7
plot "rl\results.txt" using 1 title "RL" with linespoints linestyle 1,
 "reputation\results.txt" using 1 title "RL+Reputation" with linespoints linestyle 2,
 "random\results.txt" using 1 title "Random" with linespoints linestyle 3