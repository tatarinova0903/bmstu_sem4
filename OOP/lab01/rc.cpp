#include "rc.h"
#include <QMessageBox>

void print_warning(return_code rc)
{
    if (rc == OK)
        return;
    QMessageBox mbox;
    switch (rc) {
    case ERR_OPEN_FILE:
       mbox.setText("При открытии файла произошла ошибка. Попробуйте еще раз.");
        break;
    case ERR_EMPTY:
        mbox.setText("Пустая моделью невозможно нарисоввть.");
        break;
    case ERR_INPUT:
        mbox.setText("INPUT");
        break;
    case ERR_MEMORY:
        mbox.setText("MEMORY");
        break;
    case ERR_PARAMETR:
        mbox.setText("PARAMETR");
        break;
    default:
        mbox.setText("UNKNOWN ERROR");
        break;
    }
    mbox.exec();
}
