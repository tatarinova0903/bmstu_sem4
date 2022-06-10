#include "lift.hpp"

Lift::Lift(const int floors)
{
    QObject::connect(
        &this->controller, &Controller::controllerDepartured,
        &cabin, &Cabin::slotMoving
    );
    QObject::connect(
        &this->cabin, &Cabin::cabinMoving,
        &this->controller, &Controller::slotStartMoving
    );
    QObject::connect(
        &this->controller, &Controller::controllerReachedTarget,
        &cabin, &Cabin::slotReadyToStop
    );
    QObject::connect(
        &this->cabin, &Cabin::cabinReadyToMove,
        &this->controller, &Controller::slotFree
    );

    for (int i = 1; i <= floors; ++i)
    {
        this->buttons.push_back(std::make_shared<MyButton>(i));
        QObject::connect(
            this->buttons[i - 1].get(), &MyButton::buttonPressed,
            &this->controller, &Controller::slotAddingCall
        );
        QObject::connect(
            &this->controller, &Controller::controllerReachedTarget,
            this->buttons[i - 1].get(), &MyButton::slotInactive
        );
    }
}

const std::vector<std::shared_ptr<MyButton>> &Lift::getButtons() const
{
    return this->buttons;
}
