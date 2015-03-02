set terminal postscript eps
set grid
#set palette gray
set output "reputation.eps"
set encoding utf8
set key inside left top vertical
set xlabel "Кількість опрацьованих запитів"
set ylabel "Значення репутації провайдерів"
#set yrange [0:1]
set style line 1 lt 1 pt -1

plot 'reputationV\reputations.txt' using 1 title "0" with linespoints, \
'reputationV\reputations.txt' using 2 title "1", \
'reputationV\reputations.txt' using 3 title "2", \
'reputationV\reputations.txt' using 4 title "3", \
'reputationV\reputations.txt' using 5 title "4", \
'reputationV\reputations.txt' using 6 title "5", \
'reputationV\reputations.txt' using 7 title "6", \
'reputationV\reputations.txt' using 8 title "7", \
'reputationV\reputations.txt' using 9 title "8", \
'reputationV\reputations.txt' using 10 title "9"