from model.TripleTable import *


def menu():
    print("Menu:\n"
          "    0 - EXIT\n"
          "    1 - perform interpolation\n"
          "    2 - print table with data")
    return input("Input command: ")


if __name__ == '__main__':
    table = TripleTable()
    try:
        filename = "/Users/daria/Documents/ИУ7/семестр4/ВычАлг/Лабы/lab2/lab_02/data/t.txt"
        table.inputFromTXTFile(filename)
        print("INPUT DATA ---> SUCCESS")
    except FileNotFoundError:
        print('Error with filename')
    except ValueError:
        print('Error with data in file')

    mode = menu()
    while mode != tools.MODE_EXIT:
        if mode == tools.MODE_PRINT_TABLE:
            table.print()

        elif mode == tools.MODE_INTERPOLATION:
            try:
                x = float(input('Input x: '))
                y = float(input('Input y: '))
                z = float(input('Input z: '))
                nx = int(input('Input nx: '))
                ny = int(input('Input ny: '))
                nz = int(input('Input nz: '))
            except:
                print('Invalid input')
                mode = menu()
                continue
            res = table.tripleNewtonInter(x, y, z, nz, ny, nz, True)
            print('result =', res)

        mode = menu()
