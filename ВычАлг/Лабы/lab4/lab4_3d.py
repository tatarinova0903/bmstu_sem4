from matplotlib.pyplot import *
import random as rd 
from numpy import linspace
from mpl_toolkits.mplot3d.axes3d import *
# Кол-во точек в таблице
LEN_T = 20

# Функция для заполнения таблицы рандомными значениями
def table_():
    x_arr = []
    y_arr = []
    z_arr = []
    w_arr = []
    for i in range(LEN_T):
        x = rd.random() * 10
        y = rd.random() * 10
        z = rd.random() * 10
        x_arr.append(x)
        y_arr.append(y)
        z_arr.append(z)
        w_arr.append(1)
    return x_arr, y_arr, z_arr, w_arr

# Печать таблицы
def print_table(x, y, z, ro):
    length = len(x)
    print("|    №   |    x    |    y    |    z    |    W    |")
    # with open('in_3d.txt', 'w') as f:
    #     f.write('[')
    for i in range(length):
        print("|{:^8d}| {:^7.4f} | {:^7.4f} | {:^7.4f} | {:^7.4f} |".format(i + 1, x[i], y[i], z[i], ro[i]))
        #     f.write("[{:f}, {:f}, {:f}, {:f}],\\\n".format(x[i], y[i], z[i], ro[i]))
        # f.write(']')
    print()

# Матрица СЛАУ
def makeSLAEmatrix(matrix, n):
    N = len(matrix)
    rn = 0
    for i in range(0, n + 1):
        rn += i + 1
    res = [[0 for i in range(0, rn)] for j in range(0, rn)]
    col = [0 for i in range(0, rn)]
    v = 0
    for i in range(0, n + 1):
        for j in range(0, i + 1):
            for h in range(N):
                c = 0
                for k in range(0, n + 1):
                    for l in range(0, k + 1):
                        coef = matrix[h][3] * matrix[h][0] ** (i - j) * matrix[h][1] ** j                
                        res[v][c] += coef * matrix[h][0] ** (k - l) * matrix[h][1] ** l
                        c += 1
                col[v] += coef * matrix[h][2]
            v += 1
    for i in range(len(col)):
        res[i].append(col[i])
    return res

# Функция метод Гаусс
def method_gauss(mat, degree):
    matrix = makeSLAEmatrix(mat, degree)
    n = len(matrix)
    for k in range(n):
        for i in range(k + 1, n):
            if matrix[k][k] != 0:
                coeff = -(matrix[i][k] / matrix[k][k])
            for j in range(k, n + 1):
                matrix[i][j] += coeff * matrix[k][j]
    a = [0 for i in range(n)]
    for i in range(n - 1, -1, -1):
        for j in range(n - 1, i, -1):
            matrix[i][n] -= a[j] * matrix[i][j]
        # print(matrix[i][i])
        if matrix[i][i] != 0:
            a[i] = matrix[i][n] / matrix[i][i]
    return a

# Главная функция
def main():
    table = [[3.088745, 5.116682, 4.660414, 1.000000],\
            # [4.369200, 7.398475, 6.876945, 1.000000],\
            [0.539903, 6.848681, 2.455548, 1.000000],\
            # [9.808044, 8.175441, 1.048840, 1.000000],\
            [3.433558, 5.510738, 7.682606, 1.000000],\
            # [9.653828, 5.688054, 2.347617, 1.000000],\
            [4.073592, 3.378070, 2.412444, 1.000000],\
            # [5.080795, 7.041563, 0.872745, 1.000000],\
            # [6.829687, 0.610833, 2.884739, 1.000000],\
            [1.777214, 1.908768, 8.587959, 1.000000],\
            # [7.480713, 0.243457, 1.442902, 1.000000],\
            # [3.796658, 7.862816, 7.252729, 1.000000],\
            [2.263831, 6.160919, 7.872041, 1.000000],\
            # [4.065092, 7.036457, 0.957071, 1.000000],\
            # [4.349494, 9.579039, 6.245893, 1.000000],\
            # [9.307134, 6.930191, 2.992184, 1.000000],\
            # [7.863257, 7.451113, 5.863352, 1.000000],\
            [6.825248, 2.465977, 6.785700, 1.000000],\
            # [7.551886, 7.677976, 5.501779, 1.000000],\
            # [2.318690, 3.323841, 9.902350, 1.000000],\
            ]

    x_coor = [table[i][0] for i in range(len(table))]
    y_coor = [table[i][1] for i in range(len(table))]
    z_coor = [table[i][2] for i in range(len(table))]
    w = [table[i][3] for i in range(len(table))]

    print("Принятая таблица:\n")
    print_table(x_coor, y_coor, z_coor, w)

    degree = -1
    while degree < 0:
        try:
            degree = int(input("Введите степень аппроксимирующего полинома: "))
        except Exception:
            print("Неверная степень. Степень должна быть больше 0.\n")

    fig = figure()
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(x_coor, y_coor, z_coor)
    answer = method_gauss(table, degree)
    print("Коэффициенты А полинома: ", answer)
    x = list(linspace(min(x_coor), max(x_coor), 20))
    y = list(linspace(min(y_coor), max(y_coor), 20))
    res_x = []
    res_y = []
    res_z = []
    for elx in x:
        for ely in y:
            elz = 0
            i = 0
            for k in range(0, degree + 1):
                for l in range(0, k + 1):
                    elz += answer[i] * elx ** (k - l) * ely ** l
                    i += 1
            res_x.append(elx)
            res_y.append(ely)
            res_z.append(elz)
    ax.plot_trisurf(res_x, res_y, res_z, color='r', alpha=0.4)
    ax.set_xlabel('X axis')
    ax.set_ylabel('Y axis')
    ax.set_zlabel('Z axis')

    title("Трехмерная аппроксимация")
    show()

main()