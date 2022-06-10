#include "mybutton.h"

void MyButton::slotActive()
{
    if (this->status == INACTIVE)
    {
        this->status = ACTIVE;
        emit buttonPressed(this->floor);
    }
}

void MyButton::slotInactive(const int floor)
{
    if (this->status == ACTIVE && floor == this->floor)
    {
        this->status = INACTIVE;
        emit buttonUnpressed(this->floor);
    }
}
