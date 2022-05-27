#include "lift.h"

Lift::Lift()
{
    QObject::connect(&cabin, SIGNAL(pass_target_floor(int)), &control, SLOT(achive_floor(int)));
    QObject::connect(&cabin, SIGNAL(passing_floor(int,Direction)), &control, SLOT(pass_floor(int,Direction)));
    QObject::connect(&control, SIGNAL(set_target(int)), &cabin, SLOT(set_target(int)));
}

void Lift::set_floor(int i)
{
    control.set_new_target(i);
}

void Lift::set_text_edit(QTextEdit *t)
{
    text = t;
    text->append("Начало работы лифта.");
    cabin.set_text_edit(text);
    control.set_text_edit(text);
}
