import { useState } from 'react';
import { Trash } from "lucide-react"
import GpApi from '@/lib/gp_service';
import { Button } from '../ui/button';
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from '../ui/alert-dialog';

// New DeleteButton component
function DeleteButton({ gpCode, row, table }: { gpCode: string, row: any, table: any }) {
  const [isOpen, setIsOpen] = useState(false);

  const handleDeleteConfirm = async () => {
    if (gpCode) {
      try {
        await GpApi.delete(gpCode); // Call the API delete function

        table.options.meta?.reload_data(); // Refresh the data
        table.options.meta?.toast({
          title: "Grand Prix deleted",
          description: `Successfully deleted Grand Prix ${row.original.name} - ${row.original.country} from the application.`,
        });
      } catch (error) {
        console.error("Error deleting GP:", error);
        table.options.meta?.toast({
          variant: "destructive",
          title: "Error deleting Grand Prix",
          description: `Something went wrong when trying to delete Grand Prix ${row.original.name} - ${row.original.country}.`,
        });
      }
      setIsOpen(false); // Close the dialog after the action
    }
  };

  return (
    <div className="flex flex-col justify-center items-center gap-y-0.5">
      <Button onClick={() => setIsOpen(true)} className="bg-accent px-3 py-1">
        <Trash />
      </Button>

      <AlertDialog open={isOpen} onOpenChange={setIsOpen}>
        <AlertDialogTrigger />
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you sure you want to delete?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. The Grand Prix {row.original.name} - {row.original.country} will be permanently deleted.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setIsOpen(false)}>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={() => { handleDeleteConfirm(); }} className='bg-accent'>Delete</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
};



export default DeleteButton;
