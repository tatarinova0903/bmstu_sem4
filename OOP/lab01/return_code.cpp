#include <QMessageBox>
#include "return_code.h"

void print_warning(return_code rc)
{
    QMessageBox mbox;
    switch (rc) {
    case OK:
        break;
    case ERR_OPEN_FILE:
        mbox.setText("Ошибка при открытии файла");
        break;
    case ERR_EMPTY:
        mbox.setText("Задана пустая модель");
        break;
    case ERR_INPUT:
        mbox.setText("Ошибка входных данных");
        break;
    case ERR_MEMORY:
        mbox.setText("Ошибка при работе с памятью");
        break;
    case ERR_PARAMETR:
        mbox.setText("Неверно переданный параметр");
        break;
    default:
        mbox.setText("UNKNOWN ERROR");
        break;
    }
    mbox.exec();
}
