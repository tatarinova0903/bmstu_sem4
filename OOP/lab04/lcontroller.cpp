#include "lcontroller.h"

lcontroller::lcontroller(QObject *parent):
    cur_floor(1),
    cur_d(NONE),
    state(FREE)
{
    for ( int i = 0; i < FLOORS_NUMBERS; i++)
    {
        targets[i] = false;
    }
}
void lcontroller::set_text_edit(QTextEdit *t)
{
    text = t;
    text->append("Контроллер подключен.");
}

void lcontroller::set_new_target(int floor)
{
    state = IN_PROCESS;
    targets[floor - 1] = true;
    find_next(floor);
    emit set_target(floor);

}
void lcontroller::pass_floor(int floor, Direction d)
{
    cur_floor = floor;
    cur_d = d;
    text->append("Лифт на " + QString::number(floor) + " этаже. ");
}
void lcontroller::achive_floor(int floor)
{
    if (state == IN_PROCESS)
    {
        targets[floor-1] = false;
        if (find_next(floor))
            emit set_target(floor);
        else
            state = FREE;
    }
}

bool lcontroller::find_next(int &floor)
{
    int step = -1;
    if (cur_d == UP)
        step = 1;
    for (int i = cur_floor; i <= FLOORS_NUMBERS && i > 0; i += step)
    {
        if (targets[i - 1])
        {
            floor = i;
            return true;
        }
    }
    step = -step;
    for (int i = cur_floor; i <= FLOORS_NUMBERS && i > 0; i+= step)
    {
        if (targets[i - 1])
        {
            floor = i;
            return true;
        }
    }
    return false;

}
