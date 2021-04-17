import sys
import wordninja

if __name__ == '__main__':
    a = []


    for i in range(1, len(sys.argv)):
        a.append(sys.argv[i])

    strlst = a[0].split(" ")
    for str in strlst:
        print(wordninja.split(str),end = "")
