set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 500, 350 
set output "histograms2.png"
set boxwidth 0.9 absolute
set style fill   solid 1.00 border lt -1
set key inside left top vertical Right noreverse noenhanced autotitles nobox
set style histogram clustered gap 1 title  offset character 0, 0, 0
set datafile missing '-'
set style data histograms
set xtics border in scale 0,0 nomirror rotate by -45  offset character 0, 0, 0 autojustify
set xtics  norangelimit font ",8"
set xtics   ()
set title "Test for hystograms" 
set yrange [ 0.00000 : 1000. ] noreverse nowriteback
i = 22
plot 'reputationR\hystogram2.txt' using 2:xtic(1) title "Reputation (R)", 'reputationV\hystogram2.txt' using 2:xtic(1) title "Reputation", 'rl\hystogram2.txt' u 2:xtic(1) title "RL", 'random\hystogram2.txt' u 2:xtic(1) title "Random"
