for I in `seq 1 1000000`
do
    var=$(shuf -i 1-7 -n 1)
    delay=0.01
    case "$var" in
        1)
            echo "hi" >> log.log
            sleep $delay;;
        2)
            echo "ni hao" >> log.log
            sleep $delay;;
        3)
            echo "hola" >> log.log
            sleep $delay;;
        4)
            echo "ei gude" >> log.log
            sleep $delay;;
        5)
            echo "jo wie?" >> log.log
            sleep $delay;;
        6)
            echo "hello" >> log.log
            sleep $delay;;
        7)
            echo "goedendag" >> log.log
            sleep $delay;;
    esac
done