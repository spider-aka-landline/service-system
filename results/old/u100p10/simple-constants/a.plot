set terminal pngcairo transparent enhanced
set output "average.png"
set encoding koi8r
set xlabel "Количество обработанных запросов"
set ylabel "Оценка сервиса"
set yrange [0:1]
set style line 1 lt 1 pt 7
plot 'rl\results.txt' using 1 title "RL" with linespoints linestyle 1, 'reputationV\results.txt' using 1 title "Reputation" with linespoints linestyle 2, 'reputationR\results.txt' using 1 title "Reputation (R)" with linespoints linestyle 3, 'random\results.txt' using 1 title "Random" with linespoints linestyle 4