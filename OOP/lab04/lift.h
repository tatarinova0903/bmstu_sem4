#ifndef LIFT_H
#define LIFT_H

#include <QObject>
#include "lcabin.h"
#include "lcontroller.h"

class Lift: public QObject
{
    Q_OBJECT
public:
    Lift();
    void set_floor(int i);
    void set_text_edit(QTextEdit *t);
private:
    lcabin cabin;
    lcontroller control;
    QTextEdit *text;
};

#endif // LIFT_H
