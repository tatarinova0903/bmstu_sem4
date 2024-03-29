from model.tools import *
from prettytable import PrettyTable
from model.DoubleTable import *


class TripleTable:
    def __init__(self):
        self.z = []     # одномерный массив
        self.xy = []    # одномерный массив из doubleTable

    def inputFromTXTFile(self, filename=FILE_WITH_DOUBLE_TABLE):
        with open(filename, 'r') as fin:
            isInputTable = True
            while isInputTable:
                self.xy.append(DoubleTable())
                self.z.append(float(fin.readline()))
                isInputTable = self.xy[-1].inputFromOpenTXTFile(fin)

    def print(self):
        for i, z in enumerate(self.z):
            print(f'Z = {z}')
            self.xy[i].print()
            print('   ')

    def tripleNewtonInter(self, x, y, z, nx, ny, nz, showComments=False):
        indZStart, indZEnd = findNearestInd(self.z, z, nz)
        if showComments:
            print('indZStart =', indZStart, '; indZEnd =', indZEnd)

        ZTable = []
        for k in range(indZStart, indZEnd):
            ZTable += [float(self.xy[k].doubleNewtonInter(x, y, nx, ny))]

        # if showComments:
        #     print('ZTable =', ZTable)

        # теперь последняя интерполяция по z и ZTable
        tableForNewton = [[self.z[i] for i in range(indZStart, indZEnd)], ZTable]
        tableNewton, polStr, pol = interpolationNewton(tableForNewton, y)
        return pol
