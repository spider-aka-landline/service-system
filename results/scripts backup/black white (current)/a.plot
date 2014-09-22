set terminal postscript eps
set palette gray
set output "average.eps"
set encoding utf8
set title "Сравнение стратегий выбора провайдера" 
set key inside right bottom vertical
set xlabel "Количество обработанных запросов"
set ylabel "Оценка сервиса"
#set yrange [0:1]
set style line 1 lt 1 pt -1
plot 'rl\results.txt' using 1 title "RL" with linespoints linestyle 1 pt -1, 'reputationV\results.txt' using 1 title "Reputation" with linespoints linestyle 2  pt -1, 'reputationR\results.txt' using 1 title "Reputation (R)" with linespoints linestyle 3  pt -1, 'random\results.txt' using 1 title "Random" with linespoints linestyle 4  pt -1 #4