#include "lcabin.h"

lcabin::lcabin(QObject *parent):
    state(STAY),
    current_floor(1),
    d(NONE)
{
    one_floor_Timer.setSingleShot(true);

    QObject::connect(&one_floor_Timer,SIGNAL(timeout()),this, SLOT(cabin_moving()));
    QObject::connect(&doors, SIGNAL(closed_doors()),this, SLOT(cabin_stopping()));
    QObject::connect(this, SIGNAL(cabin_stop()), this, SLOT(cabin_stopping()));
    QObject::connect(this, SIGNAL(go()), this, SLOT(cabin_moving()));
}
void lcabin::set_text_edit(QTextEdit *t)
{
    text = t;
    doors.set_text_edit(text);
    text->append("Кабина подключена.");
}

void lcabin::set_target(int floor)
{
    state = SET_TARGET;
    target_floor = floor;
    if (current_floor == target_floor)
    {
        emit cabin_stop();
    }
    else if (current_floor < target_floor)
    {
        d = UP;
        emit go();
    }
    else
    {
        d = DOWN;
        emit go();
    }
}
void lcabin::cabin_stopping()
{
    if (state == MOVING || state == SET_TARGET)
    {
        one_floor_Timer.stop();
        state = STAY;
        emit doors.open_doors();
    }
    else if (state == STAY)
    {
         emit pass_target_floor(current_floor);
    }
}
void lcabin::cabin_moving()
{
    if (state == SET_TARGET)
    {
        state = MOVING;
        if (current_floor == target_floor)
        {
            emit cabin_stop();
        }
        else if (!one_floor_Timer.isActive())
        {
             one_floor_Timer.start(ONE_FLOOR_TIME);
             emit passing_floor(current_floor,d);
        }
    }
    else if (state == MOVING)
    {

        if (current_floor < target_floor)
        {
            d = UP;
        }
        else
        {
            d = DOWN;
        }

        current_floor += d;
        emit passing_floor(current_floor,d);

        if (current_floor == target_floor)
        {
            emit cabin_stop();
        }
        else
        {
            one_floor_Timer.start(ONE_FLOOR_TIME);
        }
    }
}

