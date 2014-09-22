set terminal postscript font "arial,10" fontscale 1.0
set output "histograms2.eps"
set palette gray
# set boxwidth 0.9 absolute
set style fill   solid 0.00 border lt -1
set key inside left top vertical Right noreverse noenhanced autotitles nobox
set style histogram clustered gap 1 title  offset character 0, 0, 0
set datafile missing '-'
set style data histograms
# TODO: do smth with number of symbols after dot (e.g. 0.43 instead of 0.43398757)
# TODO: remove "rotate"
set xtics border in scale 0,0 nomirror rotate by -45  offset character 0, 0, 0 autojustify
set xtics  norangelimit font ",8"
set xtics   ()
set title "Распределение обработанных запросов по оценкам их характеристик" 
set xlabel "Оценка характеристик сервиса"
set ylabel "Количество запросов"
set yrange [ 0.00000 : 1000. ] noreverse nowriteback
i = 22
plot 'reputationR\hystogram2.txt' using 2:xtic(1) title "Reputation (R)", 'reputationV\hystogram2.txt' using 2:xtic(1) title "Reputation", 'rl\hystogram2.txt' u 2:xtic(1) title "RL", 'random\hystogram2.txt' u 2:xtic(1) title "Random"
