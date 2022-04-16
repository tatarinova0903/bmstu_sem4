#ifndef LIST_ITERATOR_IMPLEMENT_H
#define LIST_ITERATOR_IMPLEMENT_H

#include "list_iterator.h"

using namespace iterator;

template<typename T>
list_iterator<T>::list_iterator(const list_iterator<T>& iter):
    base_iterator<T>(iter.ptr)
{}

template<typename T>
list_iterator<T>::list_iterator(shared_ptr<Node<T>> ptr):
    base_iterator<T>(ptr)
{}

template<typename T>
T& list_iterator<T>::operator *()
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    return this->ptr.lock()->get_obj();
}

template<typename T>
shared_ptr<Node<T>> list_iterator<T>::operator ->()
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    return this->ptr.lock();
}

template<typename T>
const T& list_iterator<T>::operator *() const
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    return this->ptr.lock()->get_obj();
}

template<typename T>
const shared_ptr<Node<T>> list_iterator<T>::operator ->() const
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    return this->ptr.lock();
}


#endif // LIST_ITERATOR_IMPLEMENT_H
