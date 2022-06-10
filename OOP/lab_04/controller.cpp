#include "controller.hpp"

Controller::Controller()
{
    connect(this, &Controller::controllerNeedsDeparture, this, &Controller::slotDeparture);
    connect(this, &Controller::controllerFreed, this, &Controller::slotDeparture);

    this->floorTimer.setInterval(FLOOR_PASS_TIME);
    connect(&this->floorTimer, &QTimer::timeout, this, &Controller::slotMoving);
}

void Controller::slotAddingCall(const int floor)
{
    Status prevStatus = this->status;
    this->status = ADDING_CALL;
    this->targets.push_back(floor);
    if (prevStatus == FREE)
    {
        qDebug() << "Лифт вызван на этаж №" << floor;
        emit controllerNeedsDeparture();
    }
    else
    {
        qDebug() << "Вызов лифта на этаж №" << floor << "добавлен в очередь.";
    }
}

void Controller::slotDeparture()
{
    if ((this->status == FREE || this->status == ADDING_CALL) && !this->targets.empty())
    {
        this->status = DEPARTURE;
        auto begin = getNewTarget();
        this->currentTarget = *begin;
        this->targets.erase(begin);
        qDebug() << "Отправление на этаж №" << this->currentTarget;
        if (this->currentFloor == this->currentTarget)
        {
            emit controllerReachedTarget(this->currentTarget);
        }
        else
        {
            emit controllerDepartured();
        }
    }
}

void Controller::slotStartMoving()
{
    if (this->status == DEPARTURE || this->status == ADDING_CALL)
    {
        this->status = START_MOVING;
        qDebug() << "Начато движение к этажу №" << this->currentTarget;
        this->direction = this->currentFloor > this->currentTarget ? -1 : 1;
        this->floorTimer.start();
    }
}

void Controller::slotMoving()
{
    if (this->status != FREE && this->status != ARRIVAL)
    {
        this->status = MOVING;
        qDebug() << "Лифт на этаже №" << this->currentFloor;
        if (this->currentFloor == this->currentTarget)
        {
            this->floorTimer.stop();
            emit controllerReachedTarget(this->currentTarget);
        }
        else
        {
            this->currentFloor += this->direction;
        }
    }
}

void Controller::slotArrival()
{
    if (this->status == ADDING_CALL || this->status == MOVING || this->status == DEPARTURE)
    {
        this->status = ARRIVAL;
        qDebug() << "Прибытие на этаж №" << this->currentFloor;
    }
}

void Controller::slotFree()
{
    if (this->status == ADDING_CALL || this->status == ARRIVAL)
    {
        this->status = FREE;
        qDebug() << "Работа окончена";
        emit controllerFreed();
    }
}

std::vector<int>::iterator Controller::getNewTarget()
{
    std::vector<int>::iterator target = this->targets.begin();
    int minDiff = FLOORS_COUNT;
    if (!this->currentTarget)
    {
        return target;
    }
    for (auto it = this->targets.begin(); it != this->targets.end(); it++)
    {
        int diff = this->currentTarget - *it;
        if (diff < minDiff)
        {
            minDiff = diff;
            target = it;
        }
    }
    return target;
}


