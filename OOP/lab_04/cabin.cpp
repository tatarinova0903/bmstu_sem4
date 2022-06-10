#include "cabin.hpp"

Cabin::Cabin()
{
    connect(&this->doors, &Doors::doorsOpened, this, &Cabin::slotBoarding);
    connect(&this->doors, &Doors::doorsClosed, this, &Cabin::slotReadyToMove);
    connect(this, &Cabin::cabinReachTarget, &this->doors, &Doors::slotOpening);
}

void Cabin::slotReadyToMove()
{
    if (this->status == BOARDING)
    {
        this->status = READY_TO_MOVE;
        qDebug() << "Кабина снова готова к отправке.";
        emit cabinReadyToMove();
    }
}

void Cabin::slotMoving()
{
    if (this->status == READY_TO_MOVE)
    {
        this->status = MOVING;
        qDebug() << "Кабина поехала.";
        emit cabinMoving();
    }
}

void Cabin::slotReadyToStop()
{
    if (this->status == MOVING || this->status == READY_TO_MOVE)
    {
        this->status = REACH_TARGET;
        qDebug() << "Кабина у цели.";
        emit cabinReachTarget();
    }
}

void Cabin::slotBoarding()
{
    if (this->status == REACH_TARGET)
    {
        this->status = BOARDING;
        qDebug() << "В кабину можно зайти или выйти из нее.";
    }
}

