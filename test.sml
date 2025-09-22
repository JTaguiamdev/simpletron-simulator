00 4003 ; jump 03
01 05 ; Start
02 05 ; Display
03 2001 ; load accumulator with value from memory 01
04 1102 ; print display
05 3601 ; Subtract immediate accumulation by 01
06 2101 ; store accumulator to 01
07 4212 ; jump to wait if accumulator is 0
08 2002 ; load accumulator with value for memory 02
09 3501 ; add immediate accumulator by 01
10 2102 ; store accumulator to memory 02
11 4003 ; jump to 03
12 4300 ; end
