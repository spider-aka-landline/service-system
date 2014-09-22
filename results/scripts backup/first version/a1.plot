set terminal pngcairo transparent enhanced
set output "average1.png"
set encoding koi8r
set xlabel "Количество обработанных запросов"
set ylabel "Оценка сервиса"
set key inside right bottom vertical Right noreverse noenhanced autotitles nobox
set style line 1 lt 1 pt 7
plot 'rl\results.txt' using 1 title "RL" with linespoints lt 1, 'reputationV\results.txt' using 1 title "Reputation" with linespoints lt 2, 'reputationR\results.txt' using 1 title "Reputation (R)" with linespoints lt 3